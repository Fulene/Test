<mat-chip-list #chipList>
  <div class="chips-container" [hidden]="!selectedItems.length" [class.w-100]="!inline">
    <mat-chip
      *ngFor="let item of selectedItems"
      [removable]="removable"
      [color]="'accent'"
      disableRipple="true"
      (removed)="onRemove(item)"
    >
      {{ getItemValue(item) }}
      <mat-icon matChipRemove *ngIf="removable">cancel</mat-icon>
    </mat-chip>
  </div>
  <input
    placeholder="{{ inputPlaceholder }}"
    #itemInput
    [formControl]="itemCtrl"
    [matAutocomplete]="auto"
    [matChipInputFor]="chipList"
    (matChipInputTokenEnd)="addChip($event)"
  />
</mat-chip-list>
<mat-autocomplete #auto="matAutocomplete" (optionSelected)="onSelect($event)">
  <ng-container *ngFor="let item of filteredItems | async">
    <mat-option *ngIf="!selectedItems.includes(item)" [value]="item">
      {{ getItemValue(item) }}
      <!-- {{item}} -->
    </mat-option>
  </ng-container>
</mat-autocomplete>


export class SoChipsComponent implements DoCheck {
  @Input() items: any[] = [];
  @Input() selectedItems: any[] = [];
  @Input() selectedItemIds: number[] = [];
  @Input() inputPlaceholder: string = '';
  @Input() removable: boolean = true;
  @Input() propToDisplay?: string; // ex : "name" to display "myObj.name". Don't fill this parameter if items is an array of primitive
  @Input() sortBy: any;
  @Input() allowNewItems?: boolean;
  @Input() inline?: boolean;
  @Output() selectedItemsChange: EventEmitter<any[]> = new EventEmitter<any[]>();

  filteredItems?: Observable<string[]>;
  itemCtrl = new FormControl();

  itDiffers: any;

  @ViewChild('itemInput') itemInput!: ElementRef<HTMLInputElement>;

  constructor(itDiffers: IterableDiffers) {
    this.itDiffers = itDiffers.find(this.items).create(undefined);
  }

  ngDoCheck() {
    const itChange = this.itDiffers.diff(this.items);
    if (itChange) this.initOrUpdateFilteredItems();

    if (this.selectedItemIds.length) this.selectedItems = this.items.filter((i) => this.selectedItemIds.includes(i.id));
  }

  initOrUpdateFilteredItems() {
    this.filteredItems = this.itemCtrl.valueChanges.pipe(
      startWith(null),
      map((item: string | null) =>
        item ? this._filter(item) : this.sortBy ? _.sortBy(this.items.slice(), this.sortBy) : this.items
      )
    );
  }

  getItemValue(item: any) {
    return this.propToDisplay ? ToolboxService.getObjKeyValue(item, this.propToDisplay)?.value : item;
  }

  private _filter(value?: string): string[] {
    const filterValue = value?.toString().toLowerCase();

    let filteredItems = this.items.filter((item) =>
      (this.propToDisplay ? ToolboxService.getObjKeyValue(item, this.propToDisplay)?.value : item)
        .toString()
        .toLowerCase()
        .includes(filterValue)
    );

    return this.sortBy ? _.sortBy(filteredItems, this.sortBy) : filteredItems;
  }

  addChip(event: MatChipInputEvent): void {
    const value: string = (event.value || '').trim();

    // Add our item
    if (value && (this.allowNewItems || this.items.includes(value))) {
      this.addItem(value);
    }
  }

  onSelect(event: MatAutocompleteSelectedEvent): void {
    this.addItem(event.option.value);
  }

  addItem(item: any) {
    this.selectedItems.push(item);
    this.selectedItemsChange.emit(this.selectedItems);

    this.itemInput.nativeElement.value = '';
    this.itemInput.nativeElement.blur();
    this.itemCtrl.setValue(null);
  }

  onRemove(item: string): void {
    const index = this.selectedItems.indexOf(item);

    if (index >= 0) {
      this.selectedItems.splice(index, 1);
      this.selectedItemsChange.emit(this.selectedItems);
    }
  }
}