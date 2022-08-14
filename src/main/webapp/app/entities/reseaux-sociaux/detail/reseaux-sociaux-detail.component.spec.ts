import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReseauxSociauxDetailComponent } from './reseaux-sociaux-detail.component';

describe('ReseauxSociaux Management Detail Component', () => {
  let comp: ReseauxSociauxDetailComponent;
  let fixture: ComponentFixture<ReseauxSociauxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReseauxSociauxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reseauxSociaux: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReseauxSociauxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReseauxSociauxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reseauxSociaux on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reseauxSociaux).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
