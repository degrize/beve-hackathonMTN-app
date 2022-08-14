import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorieCreateur, NewCategorieCreateur } from '../categorie-createur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorieCreateur for edit and NewCategorieCreateurFormGroupInput for create.
 */
type CategorieCreateurFormGroupInput = ICategorieCreateur | PartialWithRequiredKeyOf<NewCategorieCreateur>;

type CategorieCreateurFormDefaults = Pick<NewCategorieCreateur, 'id' | 'createurAfricains'>;

type CategorieCreateurFormGroupContent = {
  id: FormControl<ICategorieCreateur['id'] | NewCategorieCreateur['id']>;
  categorie: FormControl<ICategorieCreateur['categorie']>;
  description: FormControl<ICategorieCreateur['description']>;
  createurAfricains: FormControl<ICategorieCreateur['createurAfricains']>;
};

export type CategorieCreateurFormGroup = FormGroup<CategorieCreateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorieCreateurFormService {
  createCategorieCreateurFormGroup(categorieCreateur: CategorieCreateurFormGroupInput = { id: null }): CategorieCreateurFormGroup {
    const categorieCreateurRawValue = {
      ...this.getFormDefaults(),
      ...categorieCreateur,
    };
    return new FormGroup<CategorieCreateurFormGroupContent>({
      id: new FormControl(
        { value: categorieCreateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      categorie: new FormControl(categorieCreateurRawValue.categorie),
      description: new FormControl(categorieCreateurRawValue.description),
      createurAfricains: new FormControl(categorieCreateurRawValue.createurAfricains ?? []),
    });
  }

  getCategorieCreateur(form: CategorieCreateurFormGroup): ICategorieCreateur | NewCategorieCreateur {
    return form.getRawValue() as ICategorieCreateur | NewCategorieCreateur;
  }

  resetForm(form: CategorieCreateurFormGroup, categorieCreateur: CategorieCreateurFormGroupInput): void {
    const categorieCreateurRawValue = { ...this.getFormDefaults(), ...categorieCreateur };
    form.reset(
      {
        ...categorieCreateurRawValue,
        id: { value: categorieCreateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategorieCreateurFormDefaults {
    return {
      id: null,
      createurAfricains: [],
    };
  }
}
