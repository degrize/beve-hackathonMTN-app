import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureCreateurDetailComponent } from './nature-createur-detail.component';

describe('NatureCreateur Management Detail Component', () => {
  let comp: NatureCreateurDetailComponent;
  let fixture: ComponentFixture<NatureCreateurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureCreateurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureCreateur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureCreateurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureCreateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureCreateur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureCreateur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
