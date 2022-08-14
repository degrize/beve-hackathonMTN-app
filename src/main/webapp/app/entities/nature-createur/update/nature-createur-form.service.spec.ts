import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nature-createur.test-samples';

import { NatureCreateurFormService } from './nature-createur-form.service';

describe('NatureCreateur Form Service', () => {
  let service: NatureCreateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NatureCreateurFormService);
  });

  describe('Service methods', () => {
    describe('createNatureCreateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNatureCreateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing INatureCreateur should create a new form with FormGroup', () => {
        const formGroup = service.createNatureCreateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getNatureCreateur', () => {
      it('should return NewNatureCreateur for default NatureCreateur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNatureCreateurFormGroup(sampleWithNewData);

        const natureCreateur = service.getNatureCreateur(formGroup) as any;

        expect(natureCreateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewNatureCreateur for empty NatureCreateur initial value', () => {
        const formGroup = service.createNatureCreateurFormGroup();

        const natureCreateur = service.getNatureCreateur(formGroup) as any;

        expect(natureCreateur).toMatchObject({});
      });

      it('should return INatureCreateur', () => {
        const formGroup = service.createNatureCreateurFormGroup(sampleWithRequiredData);

        const natureCreateur = service.getNatureCreateur(formGroup) as any;

        expect(natureCreateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INatureCreateur should not enable id FormControl', () => {
        const formGroup = service.createNatureCreateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNatureCreateur should disable id FormControl', () => {
        const formGroup = service.createNatureCreateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
