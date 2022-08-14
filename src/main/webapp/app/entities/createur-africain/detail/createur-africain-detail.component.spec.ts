import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreateurAfricainDetailComponent } from './createur-africain-detail.component';

describe('CreateurAfricain Management Detail Component', () => {
  let comp: CreateurAfricainDetailComponent;
  let fixture: ComponentFixture<CreateurAfricainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateurAfricainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ createurAfricain: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CreateurAfricainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CreateurAfricainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load createurAfricain on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.createurAfricain).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
