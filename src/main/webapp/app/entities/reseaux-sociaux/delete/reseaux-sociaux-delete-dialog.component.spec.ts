jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ReseauxSociauxService } from '../service/reseaux-sociaux.service';

import { ReseauxSociauxDeleteDialogComponent } from './reseaux-sociaux-delete-dialog.component';

describe('ReseauxSociaux Management Delete Component', () => {
  let comp: ReseauxSociauxDeleteDialogComponent;
  let fixture: ComponentFixture<ReseauxSociauxDeleteDialogComponent>;
  let service: ReseauxSociauxService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReseauxSociauxDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ReseauxSociauxDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReseauxSociauxDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReseauxSociauxService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
