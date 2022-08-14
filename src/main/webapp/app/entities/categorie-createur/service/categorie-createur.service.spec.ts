import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieCreateur } from '../categorie-createur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categorie-createur.test-samples';

import { CategorieCreateurService } from './categorie-createur.service';

const requireRestSample: ICategorieCreateur = {
  ...sampleWithRequiredData,
};

describe('CategorieCreateur Service', () => {
  let service: CategorieCreateurService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorieCreateur | ICategorieCreateur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorieCreateurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CategorieCreateur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categorieCreateur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categorieCreateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategorieCreateur', () => {
      const categorieCreateur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categorieCreateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategorieCreateur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategorieCreateur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategorieCreateur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCategorieCreateurToCollectionIfMissing', () => {
      it('should add a CategorieCreateur to an empty array', () => {
        const categorieCreateur: ICategorieCreateur = sampleWithRequiredData;
        expectedResult = service.addCategorieCreateurToCollectionIfMissing([], categorieCreateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieCreateur);
      });

      it('should not add a CategorieCreateur to an array that contains it', () => {
        const categorieCreateur: ICategorieCreateur = sampleWithRequiredData;
        const categorieCreateurCollection: ICategorieCreateur[] = [
          {
            ...categorieCreateur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorieCreateurToCollectionIfMissing(categorieCreateurCollection, categorieCreateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategorieCreateur to an array that doesn't contain it", () => {
        const categorieCreateur: ICategorieCreateur = sampleWithRequiredData;
        const categorieCreateurCollection: ICategorieCreateur[] = [sampleWithPartialData];
        expectedResult = service.addCategorieCreateurToCollectionIfMissing(categorieCreateurCollection, categorieCreateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieCreateur);
      });

      it('should add only unique CategorieCreateur to an array', () => {
        const categorieCreateurArray: ICategorieCreateur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categorieCreateurCollection: ICategorieCreateur[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieCreateurToCollectionIfMissing(categorieCreateurCollection, ...categorieCreateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categorieCreateur: ICategorieCreateur = sampleWithRequiredData;
        const categorieCreateur2: ICategorieCreateur = sampleWithPartialData;
        expectedResult = service.addCategorieCreateurToCollectionIfMissing([], categorieCreateur, categorieCreateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieCreateur);
        expect(expectedResult).toContain(categorieCreateur2);
      });

      it('should accept null and undefined values', () => {
        const categorieCreateur: ICategorieCreateur = sampleWithRequiredData;
        expectedResult = service.addCategorieCreateurToCollectionIfMissing([], null, categorieCreateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieCreateur);
      });

      it('should return initial array if no CategorieCreateur is added', () => {
        const categorieCreateurCollection: ICategorieCreateur[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieCreateurToCollectionIfMissing(categorieCreateurCollection, undefined, null);
        expect(expectedResult).toEqual(categorieCreateurCollection);
      });
    });

    describe('compareCategorieCreateur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorieCreateur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategorieCreateur(entity1, entity2);
        const compareResult2 = service.compareCategorieCreateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategorieCreateur(entity1, entity2);
        const compareResult2 = service.compareCategorieCreateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategorieCreateur(entity1, entity2);
        const compareResult2 = service.compareCategorieCreateur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
