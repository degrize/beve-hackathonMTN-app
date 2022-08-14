import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../don.test-samples';

import { DonFormService } from './don-form.service';

describe('Don Form Service', () => {
  let service: DonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DonFormService);
  });

  describe('Service methods', () => {
    describe('createDonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            dateDon: expect.any(Object),
            transaction: expect.any(Object),
            createurAfricain: expect.any(Object),
            donnateur: expect.any(Object),
          })
        );
      });

      it('passing IDon should create a new form with FormGroup', () => {
        const formGroup = service.createDonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            dateDon: expect.any(Object),
            transaction: expect.any(Object),
            createurAfricain: expect.any(Object),
            donnateur: expect.any(Object),
          })
        );
      });
    });

    describe('getDon', () => {
      it('should return NewDon for default Don initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDonFormGroup(sampleWithNewData);

        const don = service.getDon(formGroup) as any;

        expect(don).toMatchObject(sampleWithNewData);
      });

      it('should return NewDon for empty Don initial value', () => {
        const formGroup = service.createDonFormGroup();

        const don = service.getDon(formGroup) as any;

        expect(don).toMatchObject({});
      });

      it('should return IDon', () => {
        const formGroup = service.createDonFormGroup(sampleWithRequiredData);

        const don = service.getDon(formGroup) as any;

        expect(don).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDon should not enable id FormControl', () => {
        const formGroup = service.createDonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDon should disable id FormControl', () => {
        const formGroup = service.createDonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
