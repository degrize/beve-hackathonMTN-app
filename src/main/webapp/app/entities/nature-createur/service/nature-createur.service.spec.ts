import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INatureCreateur } from '../nature-createur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nature-createur.test-samples';

import { NatureCreateurService } from './nature-createur.service';

const requireRestSample: INatureCreateur = {
  ...sampleWithRequiredData,
};

describe('NatureCreateur Service', () => {
  let service: NatureCreateurService;
  let httpMock: HttpTestingController;
  let expectedResult: INatureCreateur | INatureCreateur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureCreateurService);
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

    it('should create a NatureCreateur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const natureCreateur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(natureCreateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureCreateur', () => {
      const natureCreateur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(natureCreateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NatureCreateur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NatureCreateur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NatureCreateur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureCreateurToCollectionIfMissing', () => {
      it('should add a NatureCreateur to an empty array', () => {
        const natureCreateur: INatureCreateur = sampleWithRequiredData;
        expectedResult = service.addNatureCreateurToCollectionIfMissing([], natureCreateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureCreateur);
      });

      it('should not add a NatureCreateur to an array that contains it', () => {
        const natureCreateur: INatureCreateur = sampleWithRequiredData;
        const natureCreateurCollection: INatureCreateur[] = [
          {
            ...natureCreateur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNatureCreateurToCollectionIfMissing(natureCreateurCollection, natureCreateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureCreateur to an array that doesn't contain it", () => {
        const natureCreateur: INatureCreateur = sampleWithRequiredData;
        const natureCreateurCollection: INatureCreateur[] = [sampleWithPartialData];
        expectedResult = service.addNatureCreateurToCollectionIfMissing(natureCreateurCollection, natureCreateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureCreateur);
      });

      it('should add only unique NatureCreateur to an array', () => {
        const natureCreateurArray: INatureCreateur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const natureCreateurCollection: INatureCreateur[] = [sampleWithRequiredData];
        expectedResult = service.addNatureCreateurToCollectionIfMissing(natureCreateurCollection, ...natureCreateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureCreateur: INatureCreateur = sampleWithRequiredData;
        const natureCreateur2: INatureCreateur = sampleWithPartialData;
        expectedResult = service.addNatureCreateurToCollectionIfMissing([], natureCreateur, natureCreateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureCreateur);
        expect(expectedResult).toContain(natureCreateur2);
      });

      it('should accept null and undefined values', () => {
        const natureCreateur: INatureCreateur = sampleWithRequiredData;
        expectedResult = service.addNatureCreateurToCollectionIfMissing([], null, natureCreateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureCreateur);
      });

      it('should return initial array if no NatureCreateur is added', () => {
        const natureCreateurCollection: INatureCreateur[] = [sampleWithRequiredData];
        expectedResult = service.addNatureCreateurToCollectionIfMissing(natureCreateurCollection, undefined, null);
        expect(expectedResult).toEqual(natureCreateurCollection);
      });
    });

    describe('compareNatureCreateur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNatureCreateur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNatureCreateur(entity1, entity2);
        const compareResult2 = service.compareNatureCreateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNatureCreateur(entity1, entity2);
        const compareResult2 = service.compareNatureCreateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNatureCreateur(entity1, entity2);
        const compareResult2 = service.compareNatureCreateur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
