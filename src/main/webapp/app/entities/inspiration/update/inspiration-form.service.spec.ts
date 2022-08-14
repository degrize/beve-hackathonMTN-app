import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../inspiration.test-samples';

import { InspirationFormService } from './inspiration-form.service';

describe('Inspiration Form Service', () => {
  let service: InspirationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InspirationFormService);
  });

  describe('Service methods', () => {
    describe('createInspirationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInspirationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            articleInspiration: expect.any(Object),
            createurAfricains: expect.any(Object),
          })
        );
      });

      it('passing IInspiration should create a new form with FormGroup', () => {
        const formGroup = service.createInspirationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            articleInspiration: expect.any(Object),
            createurAfricains: expect.any(Object),
          })
        );
      });
    });

    describe('getInspiration', () => {
      it('should return NewInspiration for default Inspiration initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInspirationFormGroup(sampleWithNewData);

        const inspiration = service.getInspiration(formGroup) as any;

        expect(inspiration).toMatchObject(sampleWithNewData);
      });

      it('should return NewInspiration for empty Inspiration initial value', () => {
        const formGroup = service.createInspirationFormGroup();

        const inspiration = service.getInspiration(formGroup) as any;

        expect(inspiration).toMatchObject({});
      });

      it('should return IInspiration', () => {
        const formGroup = service.createInspirationFormGroup(sampleWithRequiredData);

        const inspiration = service.getInspiration(formGroup) as any;

        expect(inspiration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInspiration should not enable id FormControl', () => {
        const formGroup = service.createInspirationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInspiration should disable id FormControl', () => {
        const formGroup = service.createInspirationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
