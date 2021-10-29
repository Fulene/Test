ngAfterViewInit(): void {
    if (this.isCarouselMode)
      setTimeout(() => {
        this.setDimensions();
        this.ctx = this.canvasElt.nativeElement.getContext('2d');
        this.trackTransforms();

        let dragStart: { x: number; y: number } | null;
        let dragged: boolean;
        this.canvasElt.nativeElement.addEventListener(
          'mousedown',
          (evt: { offsetX: number; pageX: number; offsetY: number; pageY: number }) => {
            this.lastX = evt.offsetX || evt.pageX - this.canvasElt.nativeElement.offsetLeft;
            this.lastY = evt.offsetY || evt.pageY - this.canvasElt.nativeElement.offsetTop;
            dragStart = this.ctx.transformedPoint(this.lastX, this.lastY);
            dragged = false;
          },
          false
        );

        this.canvasElt.nativeElement.addEventListener(
          'mousemove',
          (evt: { offsetX: number; pageX: number; offsetY: number; pageY: number }) => {
            this.lastX = evt.offsetX || evt.pageX - this.canvasElt.nativeElement.offsetLeft;
            this.lastY = evt.offsetY || evt.pageY - this.canvasElt.nativeElement.offsetTop;
            dragged = true;
            if (dragStart) {
              var pt = this.ctx.transformedPoint(this.lastX, this.lastY);
              this.ctx.translate(pt.x - dragStart.x, pt.y - dragStart.y);
              this.redraw();
            }
          },
          false
        );

        this.canvasElt.nativeElement.addEventListener(
          'mouseup',
          (evt: { shiftKey: any }) => {
            dragStart = null;
            if (!dragged) this.zoom(evt.shiftKey ? -1 : 1);
          },
          false
        );

        this.observer = new ResizeObserver(() => {
          if (this.debounceObserverId) clearTimeout(this.debounceObserverId);
          this.debounceObserverId = _.delay(() => this.initCanvas(), appConstant.delayProcessDom);
        });
        this.observer.observe(this.itemElt.nativeElement);

        this.canvasElt.nativeElement.addEventListener('DOMMouseScroll', (e: any) => this.handleScroll(e), false);
        this.canvasElt.nativeElement.addEventListener('mousewheel', (e: any) => this.handleScroll(e), false);
      }, 0);
  }

  initCanvas() {
    this.setDimensions();
    this.lastX = this.canvasElt.nativeElement.width / 2;
    this.lastY = this.canvasElt.nativeElement.height / 2;
    this.redraw();
  }

  setDimensions() {
    let carouselEltStyle = document.getElementById('carousel-mode-' + this.uuid)!.getBoundingClientRect();
    // let carouselEltStyle = document.getElementsByClassName('carousel-mode')[0].getBoundingClientRect(); // todo => A vérif (img proportions)

    this.itemElt.nativeElement.width = carouselEltStyle.width;
    this.itemElt.nativeElement.height = carouselEltStyle.height * 0.9;

    this.imageElt.nativeElement.width = this.itemElt.nativeElement.width * 0.9;
    this.imageElt.nativeElement.height = this.itemElt.nativeElement.height * 0.85;

    this.canvasElt.nativeElement.width = this.imageElt.nativeElement.width;
    this.canvasElt.nativeElement.height = this.imageElt.nativeElement.height;
  }

  handleScroll(evt: any) {
    var delta = evt.wheelDelta ? evt.wheelDelta / 40 : evt.detail ? -evt.detail : 0;
    if (delta) this.zoom(delta);
    return evt.preventDefault() && false;
  }

  zoom(clicks: number) {
    setTimeout(() => {
      var factor = Math.pow(this.scaleFactor, clicks);
      let pt = this.ctx.transformedPoint(this.lastX, this.lastY); // pt correspond à la position x,y de la souris
      this.ctx.translate(pt.x, pt.y);
      this.ctx.scale(factor, factor);
      this.ctx.translate(-pt.x, -pt.y);
      this.redraw();
    }, 0);
  }

  redraw() {
    // Clear the entire canvas
    let p1 = this.ctx.transformedPoint(0, 0);
    let p2 = this.ctx.transformedPoint(this.canvasElt.nativeElement.width, this.canvasElt.nativeElement.height);
    this.ctx.clearRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);

    this.ctx.save();
    this.ctx.setTransform(1, 0, 0, 1, 0, 0);
    this.ctx.clearRect(0, 0, this.canvasElt.nativeElement.width, this.canvasElt.nativeElement.height);
    this.ctx.restore();
    // Calculating ratio to keep img proportions
    let hRatio = this.canvasElt.nativeElement.width / this.img.width;
    let vRatio = this.canvasElt.nativeElement.height / this.img.height;
    let ratio = Math.min(hRatio, vRatio);
    this.ctx.drawImage(
      this.img,
      0,
      0,
      this.img.width,
      this.img.height,
      this.canvasElt.nativeElement.width / 2 - (this.img.width * ratio) / 2,
      0,
      this.img.width * ratio,
      this.img.height * ratio
    );
  }

  trackTransforms() {
    let svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    let xform = svg.createSVGMatrix();
    this.ctx.getTransform = () => {
      return xform;
    };

    let savedTransforms: DOMMatrix[] = [];
    let save = this.ctx.save;
    this.ctx.save = () => {
      savedTransforms.push(xform.translate(0, 0));
      return save.call(this.ctx);
    };

    let restore = this.ctx.restore;
    this.ctx.restore = () => {
      xform = savedTransforms.pop()!;
      return restore.call(this.ctx);
    };

    let scale = this.ctx.scale;
    this.ctx.scale = (sx: number | undefined, sy: number | undefined) => {
      if (sx && xform.a <= 1 && sx < 1) return;
      xform = xform.scaleNonUniform(sx, sy);
      return scale.call(this.ctx, sx, sy);
    };

    let rotate = this.ctx.rotate;
    this.ctx.rotate = (radians: number) => {
      xform = xform.rotate((radians * 180) / Math.PI);
      return rotate.call(this.ctx, radians);
    };

    let translate = this.ctx.translate;
    this.ctx.translate = (dx: number | undefined, dy: number | undefined) => {
      xform = xform.translate(dx, dy);
      return translate.call(this.ctx, dx, dy);
    };

    let transform = this.ctx.transform;
    this.ctx.transform = (a: number, b: number, c: number, d: number, e: number, f: number) => {
      let m2 = svg.createSVGMatrix();
      m2.a = a;
      m2.b = b;
      m2.c = c;
      m2.d = d;
      m2.e = e;
      m2.f = f;
      xform = xform.multiply(m2);
      return transform.call(this.ctx, a, b, c, d, e, f);
    };

    let setTransform = this.ctx.setTransform;
    this.ctx.setTransform = (a: number, b: number, c: number, d: number, e: number, f: number) => {
      xform.a = a;
      xform.b = b;
      xform.c = c;
      xform.d = d;
      xform.e = e;
      xform.f = f;
      return setTransform.call(this.ctx, a, b, c, d, e, f);
    };

    let pt = svg.createSVGPoint();
    this.ctx.transformedPoint = (x: number, y: number) => {
      pt.x = x;
      pt.y = y;
      return pt.matrixTransform(xform.inverse());
    };
  }

  onErrorImg() {
    this.item.stringValue = this.EMPTY_CONTENT;
  }

  ngOnDestroy(): void {
    if (this.observer) this.observer.unobserve(this.itemElt.nativeElement);
  }