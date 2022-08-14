import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INatureCreateur, NewNatureCreateur } from '../nature-createur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INatureCreateur for edit and NewNatureCreateurFormGroupInput for create.
 */
type NatureCreateurFormGroupInput = INatureCreateur | PartialWithRequiredKeyOf<NewNatureCreateur>;

type NatureCreateurFormDefaults = Pick<NewNatureCreateur, 'id'>;

type NatureCreateurFormGroupContent = {
  id: FormControl<INatureCreateur['id'] | NewNatureCreateur['id']>;
  type: FormControl<INatureCreateur['type']>;
  description: FormControl<INatureCreateur['description']>;
};

export type NatureCreateurFormGroup = FormGroup<NatureCreateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NatureCreateurFormService {
  createNatureCreateurFormGroup(natureCreateur: NatureCreateurFormGroupInput = { id: null }): NatureCreateurFormGroup {
    const natureCreateurRawValue = {
      ...this.getFormDefaults(),
      ...natureCreateur,
    };
    return new FormGroup<NatureCreateurFormGroupContent>({
      id: new FormControl(
        { value: natureCreateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(natureCreateurRawValue.type),
      description: new FormControl(natureCreateurRawValue.description),
    });
  }

  getNatureCreateur(form: NatureCreateurFormGroup): INatureCreateur | NewNatureCreateur {
    return form.getRawValue() as INatureCreateur | NewNatureCreateur;
  }

  resetForm(form: NatureCreateurFormGroup, natureCreateur: NatureCreateurFormGroupInput): void {
    const natureCreateurRawValue = { ...this.getFormDefaults(), ...natureCreateur };
    form.reset(
      {
        ...natureCreateurRawValue,
        id: { value: natureCreateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NatureCreateurFormDefaults {
    return {
      id: null,
    };
  }
}
