import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieCreateurDetailComponent } from './categorie-createur-detail.component';

describe('CategorieCreateur Management Detail Component', () => {
  let comp: CategorieCreateurDetailComponent;
  let fixture: ComponentFixture<CategorieCreateurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategorieCreateurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categorieCreateur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategorieCreateurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategorieCreateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categorieCreateur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categorieCreateur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
