import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorie-createur.test-samples';

import { CategorieCreateurFormService } from './categorie-createur-form.service';

describe('CategorieCreateur Form Service', () => {
  let service: CategorieCreateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorieCreateurFormService);
  });

  describe('Service methods', () => {
    describe('createCategorieCreateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorieCreateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categorie: expect.any(Object),
            description: expect.any(Object),
            createurAfricains: expect.any(Object),
          })
        );
      });

      it('passing ICategorieCreateur should create a new form with FormGroup', () => {
        const formGroup = service.createCategorieCreateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categorie: expect.any(Object),
            description: expect.any(Object),
            createurAfricains: expect.any(Object),
          })
        );
      });
    });

    describe('getCategorieCreateur', () => {
      it('should return NewCategorieCreateur for default CategorieCreateur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategorieCreateurFormGroup(sampleWithNewData);

        const categorieCreateur = service.getCategorieCreateur(formGroup) as any;

        expect(categorieCreateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorieCreateur for empty CategorieCreateur initial value', () => {
        const formGroup = service.createCategorieCreateurFormGroup();

        const categorieCreateur = service.getCategorieCreateur(formGroup) as any;

        expect(categorieCreateur).toMatchObject({});
      });

      it('should return ICategorieCreateur', () => {
        const formGroup = service.createCategorieCreateurFormGroup(sampleWithRequiredData);

        const categorieCreateur = service.getCategorieCreateur(formGroup) as any;

        expect(categorieCreateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorieCreateur should not enable id FormControl', () => {
        const formGroup = service.createCategorieCreateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorieCreateur should disable id FormControl', () => {
        const formGroup = service.createCategorieCreateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
