import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../reseaux-sociaux.test-samples';

import { ReseauxSociauxFormService } from './reseaux-sociaux-form.service';

describe('ReseauxSociaux Form Service', () => {
  let service: ReseauxSociauxFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReseauxSociauxFormService);
  });

  describe('Service methods', () => {
    describe('createReseauxSociauxFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReseauxSociauxFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomChaine: expect.any(Object),
            lienChaine: expect.any(Object),
          })
        );
      });

      it('passing IReseauxSociaux should create a new form with FormGroup', () => {
        const formGroup = service.createReseauxSociauxFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomChaine: expect.any(Object),
            lienChaine: expect.any(Object),
          })
        );
      });
    });

    describe('getReseauxSociaux', () => {
      it('should return NewReseauxSociaux for default ReseauxSociaux initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReseauxSociauxFormGroup(sampleWithNewData);

        const reseauxSociaux = service.getReseauxSociaux(formGroup) as any;

        expect(reseauxSociaux).toMatchObject(sampleWithNewData);
      });

      it('should return NewReseauxSociaux for empty ReseauxSociaux initial value', () => {
        const formGroup = service.createReseauxSociauxFormGroup();

        const reseauxSociaux = service.getReseauxSociaux(formGroup) as any;

        expect(reseauxSociaux).toMatchObject({});
      });

      it('should return IReseauxSociaux', () => {
        const formGroup = service.createReseauxSociauxFormGroup(sampleWithRequiredData);

        const reseauxSociaux = service.getReseauxSociaux(formGroup) as any;

        expect(reseauxSociaux).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReseauxSociaux should not enable id FormControl', () => {
        const formGroup = service.createReseauxSociauxFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReseauxSociaux should disable id FormControl', () => {
        const formGroup = service.createReseauxSociauxFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
