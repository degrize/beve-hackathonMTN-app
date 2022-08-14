import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDon, NewDon } from '../don.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDon for edit and NewDonFormGroupInput for create.
 */
type DonFormGroupInput = IDon | PartialWithRequiredKeyOf<NewDon>;

type DonFormDefaults = Pick<NewDon, 'id'>;

type DonFormGroupContent = {
  id: FormControl<IDon['id'] | NewDon['id']>;
  description: FormControl<IDon['description']>;
  dateDon: FormControl<IDon['dateDon']>;
  transaction: FormControl<IDon['transaction']>;
  createurAfricain: FormControl<IDon['createurAfricain']>;
  donnateur: FormControl<IDon['donnateur']>;
};

export type DonFormGroup = FormGroup<DonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DonFormService {
  createDonFormGroup(don: DonFormGroupInput = { id: null }): DonFormGroup {
    const donRawValue = {
      ...this.getFormDefaults(),
      ...don,
    };
    return new FormGroup<DonFormGroupContent>({
      id: new FormControl(
        { value: donRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      description: new FormControl(donRawValue.description),
      dateDon: new FormControl(donRawValue.dateDon),
      transaction: new FormControl(donRawValue.transaction),
      createurAfricain: new FormControl(donRawValue.createurAfricain),
      donnateur: new FormControl(donRawValue.donnateur),
    });
  }

  getDon(form: DonFormGroup): IDon | NewDon {
    return form.getRawValue() as IDon | NewDon;
  }

  resetForm(form: DonFormGroup, don: DonFormGroupInput): void {
    const donRawValue = { ...this.getFormDefaults(), ...don };
    form.reset(
      {
        ...donRawValue,
        id: { value: donRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DonFormDefaults {
    return {
      id: null,
    };
  }
}
