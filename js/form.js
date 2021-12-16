form: FormGroup

dans constructor => fb: FormBuilder

f(): { [key: string]: AbstractControl } {
    return this.form.controls;
}

<form [formGroup]="form" (ngSubmit)="save()">
  <!-- Name -->
  <div>
    <mat-form-field appearance="legacy">
      <mat-label translate>Dashboard.Group.ManageOne.GroupName</mat-label>
      <input matInput type="text" formControlName="name" />
      <button matSuffix mat-icon-button aria-label="Clear" *ngIf="f().name?.value" (click)="f().name?.reset()">
        <mat-icon>close</mat-icon>
      </button>
    </mat-form-field>
  </div>
</form>