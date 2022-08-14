import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DonnateurDetailComponent } from './donnateur-detail.component';

describe('Donnateur Management Detail Component', () => {
  let comp: DonnateurDetailComponent;
  let fixture: ComponentFixture<DonnateurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonnateurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ donnateur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DonnateurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DonnateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load donnateur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.donnateur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
