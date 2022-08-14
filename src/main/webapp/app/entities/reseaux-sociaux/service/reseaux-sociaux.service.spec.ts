import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReseauxSociaux } from '../reseaux-sociaux.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../reseaux-sociaux.test-samples';

import { ReseauxSociauxService } from './reseaux-sociaux.service';

const requireRestSample: IReseauxSociaux = {
  ...sampleWithRequiredData,
};

describe('ReseauxSociaux Service', () => {
  let service: ReseauxSociauxService;
  let httpMock: HttpTestingController;
  let expectedResult: IReseauxSociaux | IReseauxSociaux[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReseauxSociauxService);
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

    it('should create a ReseauxSociaux', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const reseauxSociaux = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(reseauxSociaux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReseauxSociaux', () => {
      const reseauxSociaux = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(reseauxSociaux).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReseauxSociaux', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReseauxSociaux', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReseauxSociaux', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReseauxSociauxToCollectionIfMissing', () => {
      it('should add a ReseauxSociaux to an empty array', () => {
        const reseauxSociaux: IReseauxSociaux = sampleWithRequiredData;
        expectedResult = service.addReseauxSociauxToCollectionIfMissing([], reseauxSociaux);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reseauxSociaux);
      });

      it('should not add a ReseauxSociaux to an array that contains it', () => {
        const reseauxSociaux: IReseauxSociaux = sampleWithRequiredData;
        const reseauxSociauxCollection: IReseauxSociaux[] = [
          {
            ...reseauxSociaux,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReseauxSociauxToCollectionIfMissing(reseauxSociauxCollection, reseauxSociaux);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReseauxSociaux to an array that doesn't contain it", () => {
        const reseauxSociaux: IReseauxSociaux = sampleWithRequiredData;
        const reseauxSociauxCollection: IReseauxSociaux[] = [sampleWithPartialData];
        expectedResult = service.addReseauxSociauxToCollectionIfMissing(reseauxSociauxCollection, reseauxSociaux);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reseauxSociaux);
      });

      it('should add only unique ReseauxSociaux to an array', () => {
        const reseauxSociauxArray: IReseauxSociaux[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reseauxSociauxCollection: IReseauxSociaux[] = [sampleWithRequiredData];
        expectedResult = service.addReseauxSociauxToCollectionIfMissing(reseauxSociauxCollection, ...reseauxSociauxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reseauxSociaux: IReseauxSociaux = sampleWithRequiredData;
        const reseauxSociaux2: IReseauxSociaux = sampleWithPartialData;
        expectedResult = service.addReseauxSociauxToCollectionIfMissing([], reseauxSociaux, reseauxSociaux2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reseauxSociaux);
        expect(expectedResult).toContain(reseauxSociaux2);
      });

      it('should accept null and undefined values', () => {
        const reseauxSociaux: IReseauxSociaux = sampleWithRequiredData;
        expectedResult = service.addReseauxSociauxToCollectionIfMissing([], null, reseauxSociaux, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reseauxSociaux);
      });

      it('should return initial array if no ReseauxSociaux is added', () => {
        const reseauxSociauxCollection: IReseauxSociaux[] = [sampleWithRequiredData];
        expectedResult = service.addReseauxSociauxToCollectionIfMissing(reseauxSociauxCollection, undefined, null);
        expect(expectedResult).toEqual(reseauxSociauxCollection);
      });
    });

    describe('compareReseauxSociaux', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReseauxSociaux(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReseauxSociaux(entity1, entity2);
        const compareResult2 = service.compareReseauxSociaux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReseauxSociaux(entity1, entity2);
        const compareResult2 = service.compareReseauxSociaux(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReseauxSociaux(entity1, entity2);
        const compareResult2 = service.compareReseauxSociaux(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
