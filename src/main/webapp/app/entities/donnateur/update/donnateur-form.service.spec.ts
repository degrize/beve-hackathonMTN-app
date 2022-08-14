import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../donnateur.test-samples';

import { DonnateurFormService } from './donnateur-form.service';

describe('Donnateur Form Service', () => {
  let service: DonnateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DonnateurFormService);
  });

  describe('Service methods', () => {
    describe('createDonnateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDonnateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            contact1: expect.any(Object),
            contact2: expect.any(Object),
            email: expect.any(Object),
            sexe: expect.any(Object),
            dateDeNaissance: expect.any(Object),
            pays: expect.any(Object),
            dateDebut: expect.any(Object),
            forfait: expect.any(Object),
          })
        );
      });

      it('passing IDonnateur should create a new form with FormGroup', () => {
        const formGroup = service.createDonnateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            contact1: expect.any(Object),
            contact2: expect.any(Object),
            email: expect.any(Object),
            sexe: expect.any(Object),
            dateDeNaissance: expect.any(Object),
            pays: expect.any(Object),
            dateDebut: expect.any(Object),
            forfait: expect.any(Object),
          })
        );
      });
    });

    describe('getDonnateur', () => {
      it('should return NewDonnateur for default Donnateur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDonnateurFormGroup(sampleWithNewData);

        const donnateur = service.getDonnateur(formGroup) as any;

        expect(donnateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewDonnateur for empty Donnateur initial value', () => {
        const formGroup = service.createDonnateurFormGroup();

        const donnateur = service.getDonnateur(formGroup) as any;

        expect(donnateur).toMatchObject({});
      });

      it('should return IDonnateur', () => {
        const formGroup = service.createDonnateurFormGroup(sampleWithRequiredData);

        const donnateur = service.getDonnateur(formGroup) as any;

        expect(donnateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDonnateur should not enable id FormControl', () => {
        const formGroup = service.createDonnateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDonnateur should disable id FormControl', () => {
        const formGroup = service.createDonnateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
