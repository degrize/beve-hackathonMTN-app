import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInspiration, NewInspiration } from '../inspiration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInspiration for edit and NewInspirationFormGroupInput for create.
 */
type InspirationFormGroupInput = IInspiration | PartialWithRequiredKeyOf<NewInspiration>;

type InspirationFormDefaults = Pick<NewInspiration, 'id'>;

type InspirationFormGroupContent = {
  id: FormControl<IInspiration['id'] | NewInspiration['id']>;
  nom: FormControl<IInspiration['nom']>;
  articleInspiration: FormControl<IInspiration['articleInspiration']>;
};

export type InspirationFormGroup = FormGroup<InspirationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InspirationFormService {
  createInspirationFormGroup(inspiration: InspirationFormGroupInput = { id: null }): InspirationFormGroup {
    const inspirationRawValue = {
      ...this.getFormDefaults(),
      ...inspiration,
    };
    return new FormGroup<InspirationFormGroupContent>({
      id: new FormControl(
        { value: inspirationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(inspirationRawValue.nom, {
        validators: [Validators.required],
      }),
      articleInspiration: new FormControl(inspirationRawValue.articleInspiration),
    });
  }

  getInspiration(form: InspirationFormGroup): IInspiration | NewInspiration {
    return form.getRawValue() as IInspiration | NewInspiration;
  }

  resetForm(form: InspirationFormGroup, inspiration: InspirationFormGroupInput): void {
    const inspirationRawValue = { ...this.getFormDefaults(), ...inspiration };
    form.reset(
      {
        ...inspirationRawValue,
        id: { value: inspirationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InspirationFormDefaults {
    return {
      id: null,
    };
  }
}
