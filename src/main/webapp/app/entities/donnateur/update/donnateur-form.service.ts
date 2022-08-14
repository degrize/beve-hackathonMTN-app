import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDonnateur, NewDonnateur } from '../donnateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDonnateur for edit and NewDonnateurFormGroupInput for create.
 */
type DonnateurFormGroupInput = IDonnateur | PartialWithRequiredKeyOf<NewDonnateur>;

type DonnateurFormDefaults = Pick<NewDonnateur, 'id'>;

type DonnateurFormGroupContent = {
  id: FormControl<IDonnateur['id'] | NewDonnateur['id']>;
  nomDeFamille: FormControl<IDonnateur['nomDeFamille']>;
  prenom: FormControl<IDonnateur['prenom']>;
  contact1: FormControl<IDonnateur['contact1']>;
  contact2: FormControl<IDonnateur['contact2']>;
  email: FormControl<IDonnateur['email']>;
  sexe: FormControl<IDonnateur['sexe']>;
  dateDeNaissance: FormControl<IDonnateur['dateDeNaissance']>;
  pays: FormControl<IDonnateur['pays']>;
  dateDebut: FormControl<IDonnateur['dateDebut']>;
  forfait: FormControl<IDonnateur['forfait']>;
};

export type DonnateurFormGroup = FormGroup<DonnateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DonnateurFormService {
  createDonnateurFormGroup(donnateur: DonnateurFormGroupInput = { id: null }): DonnateurFormGroup {
    const donnateurRawValue = {
      ...this.getFormDefaults(),
      ...donnateur,
    };
    return new FormGroup<DonnateurFormGroupContent>({
      id: new FormControl(
        { value: donnateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomDeFamille: new FormControl(donnateurRawValue.nomDeFamille, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(donnateurRawValue.prenom),
      contact1: new FormControl(donnateurRawValue.contact1, {
        validators: [Validators.required],
      }),
      contact2: new FormControl(donnateurRawValue.contact2),
      email: new FormControl(donnateurRawValue.email),
      sexe: new FormControl(donnateurRawValue.sexe),
      dateDeNaissance: new FormControl(donnateurRawValue.dateDeNaissance),
      pays: new FormControl(donnateurRawValue.pays),
      dateDebut: new FormControl(donnateurRawValue.dateDebut),
      forfait: new FormControl(donnateurRawValue.forfait),
    });
  }

  getDonnateur(form: DonnateurFormGroup): IDonnateur | NewDonnateur {
    return form.getRawValue() as IDonnateur | NewDonnateur;
  }

  resetForm(form: DonnateurFormGroup, donnateur: DonnateurFormGroupInput): void {
    const donnateurRawValue = { ...this.getFormDefaults(), ...donnateur };
    form.reset(
      {
        ...donnateurRawValue,
        id: { value: donnateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DonnateurFormDefaults {
    return {
      id: null,
    };
  }
}
