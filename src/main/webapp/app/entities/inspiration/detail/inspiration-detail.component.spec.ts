import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InspirationDetailComponent } from './inspiration-detail.component';

describe('Inspiration Management Detail Component', () => {
  let comp: InspirationDetailComponent;
  let fixture: ComponentFixture<InspirationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InspirationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inspiration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InspirationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InspirationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inspiration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inspiration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
