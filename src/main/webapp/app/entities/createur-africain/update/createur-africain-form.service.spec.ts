import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../createur-africain.test-samples';

import { CreateurAfricainFormService } from './createur-africain-form.service';

describe('CreateurAfricain Form Service', () => {
  let service: CreateurAfricainFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateurAfricainFormService);
  });

  describe('Service methods', () => {
    describe('createCreateurAfricainFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCreateurAfricainFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            label: expect.any(Object),
            surnom: expect.any(Object),
            contact1: expect.any(Object),
            contact2: expect.any(Object),
            sexe: expect.any(Object),
            email: expect.any(Object),
            dateDeNaissance: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            adresse: expect.any(Object),
            situationMatrimoniale: expect.any(Object),
            dateDebut: expect.any(Object),
            inspirations: expect.any(Object),
            souscriptions: expect.any(Object),
          })
        );
      });

      it('passing ICreateurAfricain should create a new form with FormGroup', () => {
        const formGroup = service.createCreateurAfricainFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            label: expect.any(Object),
            surnom: expect.any(Object),
            contact1: expect.any(Object),
            contact2: expect.any(Object),
            sexe: expect.any(Object),
            email: expect.any(Object),
            dateDeNaissance: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            adresse: expect.any(Object),
            situationMatrimoniale: expect.any(Object),
            dateDebut: expect.any(Object),
            inspirations: expect.any(Object),
            souscriptions: expect.any(Object),
          })
        );
      });
    });

    describe('getCreateurAfricain', () => {
      it('should return NewCreateurAfricain for default CreateurAfricain initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCreateurAfricainFormGroup(sampleWithNewData);

        const createurAfricain = service.getCreateurAfricain(formGroup) as any;

        expect(createurAfricain).toMatchObject(sampleWithNewData);
      });

      it('should return NewCreateurAfricain for empty CreateurAfricain initial value', () => {
        const formGroup = service.createCreateurAfricainFormGroup();

        const createurAfricain = service.getCreateurAfricain(formGroup) as any;

        expect(createurAfricain).toMatchObject({});
      });

      it('should return ICreateurAfricain', () => {
        const formGroup = service.createCreateurAfricainFormGroup(sampleWithRequiredData);

        const createurAfricain = service.getCreateurAfricain(formGroup) as any;

        expect(createurAfricain).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICreateurAfricain should not enable id FormControl', () => {
        const formGroup = service.createCreateurAfricainFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCreateurAfricain should disable id FormControl', () => {
        const formGroup = service.createCreateurAfricainFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
