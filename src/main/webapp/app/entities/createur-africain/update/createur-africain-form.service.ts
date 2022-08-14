import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICreateurAfricain, NewCreateurAfricain } from '../createur-africain.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICreateurAfricain for edit and NewCreateurAfricainFormGroupInput for create.
 */
type CreateurAfricainFormGroupInput = ICreateurAfricain | PartialWithRequiredKeyOf<NewCreateurAfricain>;

type CreateurAfricainFormDefaults = Pick<
  NewCreateurAfricain,
  'id' | 'inspirations' | 'categorieCreateurs' | 'reseauxSociauxes' | 'souscriptions'
>;

type CreateurAfricainFormGroupContent = {
  id: FormControl<ICreateurAfricain['id'] | NewCreateurAfricain['id']>;
  nomDeFamille: FormControl<ICreateurAfricain['nomDeFamille']>;
  prenom: FormControl<ICreateurAfricain['prenom']>;
  label: FormControl<ICreateurAfricain['label']>;
  surnom: FormControl<ICreateurAfricain['surnom']>;
  contact1: FormControl<ICreateurAfricain['contact1']>;
  contact2: FormControl<ICreateurAfricain['contact2']>;
  sexe: FormControl<ICreateurAfricain['sexe']>;
  email: FormControl<ICreateurAfricain['email']>;
  dateDeNaissance: FormControl<ICreateurAfricain['dateDeNaissance']>;
  pays: FormControl<ICreateurAfricain['pays']>;
  ville: FormControl<ICreateurAfricain['ville']>;
  adresse: FormControl<ICreateurAfricain['adresse']>;
  situationMatrimoniale: FormControl<ICreateurAfricain['situationMatrimoniale']>;
  dateDebut: FormControl<ICreateurAfricain['dateDebut']>;
  inspirations: FormControl<ICreateurAfricain['inspirations']>;
  categorieCreateurs: FormControl<ICreateurAfricain['categorieCreateurs']>;
  reseauxSociauxes: FormControl<ICreateurAfricain['reseauxSociauxes']>;
  souscriptions: FormControl<ICreateurAfricain['souscriptions']>;
};

export type CreateurAfricainFormGroup = FormGroup<CreateurAfricainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CreateurAfricainFormService {
  createCreateurAfricainFormGroup(createurAfricain: CreateurAfricainFormGroupInput = { id: null }): CreateurAfricainFormGroup {
    const createurAfricainRawValue = {
      ...this.getFormDefaults(),
      ...createurAfricain,
    };
    return new FormGroup<CreateurAfricainFormGroupContent>({
      id: new FormControl(
        { value: createurAfricainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomDeFamille: new FormControl(createurAfricainRawValue.nomDeFamille, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(createurAfricainRawValue.prenom, {
        validators: [Validators.required],
      }),
      label: new FormControl(createurAfricainRawValue.label, {
        validators: [Validators.required],
      }),
      surnom: new FormControl(createurAfricainRawValue.surnom),
      contact1: new FormControl(createurAfricainRawValue.contact1, {
        validators: [Validators.required],
      }),
      contact2: new FormControl(createurAfricainRawValue.contact2),
      sexe: new FormControl(createurAfricainRawValue.sexe, {
        validators: [Validators.required],
      }),
      email: new FormControl(createurAfricainRawValue.email, {
        validators: [Validators.required],
      }),
      dateDeNaissance: new FormControl(createurAfricainRawValue.dateDeNaissance),
      pays: new FormControl(createurAfricainRawValue.pays, {
        validators: [Validators.required],
      }),
      ville: new FormControl(createurAfricainRawValue.ville),
      adresse: new FormControl(createurAfricainRawValue.adresse),
      situationMatrimoniale: new FormControl(createurAfricainRawValue.situationMatrimoniale),
      dateDebut: new FormControl(createurAfricainRawValue.dateDebut),
      inspirations: new FormControl(createurAfricainRawValue.inspirations ?? []),
      categorieCreateurs: new FormControl(createurAfricainRawValue.categorieCreateurs ?? []),
      reseauxSociauxes: new FormControl(createurAfricainRawValue.reseauxSociauxes ?? []),
      souscriptions: new FormControl(createurAfricainRawValue.souscriptions ?? []),
    });
  }

  getCreateurAfricain(form: CreateurAfricainFormGroup): ICreateurAfricain | NewCreateurAfricain {
    return form.getRawValue() as ICreateurAfricain | NewCreateurAfricain;
  }

  resetForm(form: CreateurAfricainFormGroup, createurAfricain: CreateurAfricainFormGroupInput): void {
    const createurAfricainRawValue = { ...this.getFormDefaults(), ...createurAfricain };
    form.reset(
      {
        ...createurAfricainRawValue,
        id: { value: createurAfricainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CreateurAfricainFormDefaults {
    return {
      id: null,
      inspirations: [],
      categorieCreateurs: [],
      reseauxSociauxes: [],
      souscriptions: [],
    };
  }
}
