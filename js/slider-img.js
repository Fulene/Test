// ===== Custom image carousel =====
// ::: JS :::
animeFigure(isNextBtn: boolean) {
    if (
      (this.currentDataIndex === 0 && !isNextBtn) ||
      (this.currentDataIndex === this.dataMock.length - 1 && isNextBtn)
    )
      return;

    if (isNextBtn) this.currentDataIndex += 1;
    else this.currentDataIndex -= 1;

    let translateCoef = '-' + ((100 / this.dataMock.length) * this.currentDataIndex + '%');
    console.log({ coef: translateCoef });
    document.getElementById('snapshot-figure')?.animate([{ transform: `translateX(${translateCoef})` }], {
      duration: 500,
      fill: 'forwards',
    });
  }

/*
::: HTML :::
 <div class="snapshot-container">
   <figure id="snapshot-figure" [ngStyle]="{ width: dataMock.length * 100 + '%' }">
     <div *ngFor="let data of dataMock; let i = index">
       <img [src]="data" />
     </div>
   </figure>
   <button class="next-img" (click)="animeFigure(true)">
     <fa-icon [icon]="['fas', 'chevron-right']"></fa-icon>
   </button>
   <button class="previous-img" (click)="animeFigure(false)">
     <fa-icon [icon]="['fas', 'chevron-left']"></fa-icon>
   </button>
 </div>

::: CSS :::
@import "/src/styles/skin-sodataviz.scss";

.snapshot-container {
  height: 100%;
  width: 100%;
  position: relative;
  figure {
    height: 100%;
    overflow-y: auto;
    display: flex;
    overflow: hidden;
    div {
      height: 100%;
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 90%;
      }
    }
  }
  button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    display: flex;
    align-items: center;
    justify-content: center;
    border: none;
    border-radius: 50%;
    background-color: $white;
    color: $chip-text-color;
    transition: $fast-transition-duration;
    width: 35px;
    height: 35px;
    color: black;
    font-size: 1.1em;
    box-shadow: 0 0 10px black;
    opacity: 0.7;
    &:hover {
      opacity: 1;
    }
  }
  .previous-img {
    left: 0;
  }
  .next-img {
    right: 0;
  }
}

*/