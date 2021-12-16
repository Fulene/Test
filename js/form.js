form: FormGroup

dans constructor => fb: FormBuilder

f(): { [key: string]: AbstractControl } {
    return this.form.controls;
}