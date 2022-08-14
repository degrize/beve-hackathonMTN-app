import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IReseauxSociaux, NewReseauxSociaux } from '../reseaux-sociaux.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReseauxSociaux for edit and NewReseauxSociauxFormGroupInput for create.
 */
type ReseauxSociauxFormGroupInput = IReseauxSociaux | PartialWithRequiredKeyOf<NewReseauxSociaux>;

type ReseauxSociauxFormDefaults = Pick<NewReseauxSociaux, 'id' | 'createurAfricains'>;

type ReseauxSociauxFormGroupContent = {
  id: FormControl<IReseauxSociaux['id'] | NewReseauxSociaux['id']>;
  nom: FormControl<IReseauxSociaux['nom']>;
  nomChaine: FormControl<IReseauxSociaux['nomChaine']>;
  lienChaine: FormControl<IReseauxSociaux['lienChaine']>;
  createurAfricains: FormControl<IReseauxSociaux['createurAfricains']>;
};

export type ReseauxSociauxFormGroup = FormGroup<ReseauxSociauxFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReseauxSociauxFormService {
  createReseauxSociauxFormGroup(reseauxSociaux: ReseauxSociauxFormGroupInput = { id: null }): ReseauxSociauxFormGroup {
    const reseauxSociauxRawValue = {
      ...this.getFormDefaults(),
      ...reseauxSociaux,
    };
    return new FormGroup<ReseauxSociauxFormGroupContent>({
      id: new FormControl(
        { value: reseauxSociauxRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(reseauxSociauxRawValue.nom, {
        validators: [Validators.required],
      }),
      nomChaine: new FormControl(reseauxSociauxRawValue.nomChaine),
      lienChaine: new FormControl(reseauxSociauxRawValue.lienChaine),
      createurAfricains: new FormControl(reseauxSociauxRawValue.createurAfricains ?? []),
    });
  }

  getReseauxSociaux(form: ReseauxSociauxFormGroup): IReseauxSociaux | NewReseauxSociaux {
    return form.getRawValue() as IReseauxSociaux | NewReseauxSociaux;
  }

  resetForm(form: ReseauxSociauxFormGroup, reseauxSociaux: ReseauxSociauxFormGroupInput): void {
    const reseauxSociauxRawValue = { ...this.getFormDefaults(), ...reseauxSociaux };
    form.reset(
      {
        ...reseauxSociauxRawValue,
        id: { value: reseauxSociauxRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReseauxSociauxFormDefaults {
    return {
      id: null,
      createurAfricains: [],
    };
  }
}
