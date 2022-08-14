import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInspiration } from '../inspiration.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../inspiration.test-samples';

import { InspirationService } from './inspiration.service';

const requireRestSample: IInspiration = {
  ...sampleWithRequiredData,
};

describe('Inspiration Service', () => {
  let service: InspirationService;
  let httpMock: HttpTestingController;
  let expectedResult: IInspiration | IInspiration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InspirationService);
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

    it('should create a Inspiration', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const inspiration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(inspiration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Inspiration', () => {
      const inspiration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(inspiration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Inspiration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Inspiration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Inspiration', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInspirationToCollectionIfMissing', () => {
      it('should add a Inspiration to an empty array', () => {
        const inspiration: IInspiration = sampleWithRequiredData;
        expectedResult = service.addInspirationToCollectionIfMissing([], inspiration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inspiration);
      });

      it('should not add a Inspiration to an array that contains it', () => {
        const inspiration: IInspiration = sampleWithRequiredData;
        const inspirationCollection: IInspiration[] = [
          {
            ...inspiration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInspirationToCollectionIfMissing(inspirationCollection, inspiration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Inspiration to an array that doesn't contain it", () => {
        const inspiration: IInspiration = sampleWithRequiredData;
        const inspirationCollection: IInspiration[] = [sampleWithPartialData];
        expectedResult = service.addInspirationToCollectionIfMissing(inspirationCollection, inspiration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inspiration);
      });

      it('should add only unique Inspiration to an array', () => {
        const inspirationArray: IInspiration[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const inspirationCollection: IInspiration[] = [sampleWithRequiredData];
        expectedResult = service.addInspirationToCollectionIfMissing(inspirationCollection, ...inspirationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inspiration: IInspiration = sampleWithRequiredData;
        const inspiration2: IInspiration = sampleWithPartialData;
        expectedResult = service.addInspirationToCollectionIfMissing([], inspiration, inspiration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inspiration);
        expect(expectedResult).toContain(inspiration2);
      });

      it('should accept null and undefined values', () => {
        const inspiration: IInspiration = sampleWithRequiredData;
        expectedResult = service.addInspirationToCollectionIfMissing([], null, inspiration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inspiration);
      });

      it('should return initial array if no Inspiration is added', () => {
        const inspirationCollection: IInspiration[] = [sampleWithRequiredData];
        expectedResult = service.addInspirationToCollectionIfMissing(inspirationCollection, undefined, null);
        expect(expectedResult).toEqual(inspirationCollection);
      });
    });

    describe('compareInspiration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInspiration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInspiration(entity1, entity2);
        const compareResult2 = service.compareInspiration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInspiration(entity1, entity2);
        const compareResult2 = service.compareInspiration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInspiration(entity1, entity2);
        const compareResult2 = service.compareInspiration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
