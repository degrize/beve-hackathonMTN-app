import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDon } from '../don.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../don.test-samples';

import { DonService, RestDon } from './don.service';

const requireRestSample: RestDon = {
  ...sampleWithRequiredData,
  dateDon: sampleWithRequiredData.dateDon?.format(DATE_FORMAT),
};

describe('Don Service', () => {
  let service: DonService;
  let httpMock: HttpTestingController;
  let expectedResult: IDon | IDon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonService);
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

    it('should create a Don', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const don = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(don).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Don', () => {
      const don = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(don).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Don', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Don', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Don', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonToCollectionIfMissing', () => {
      it('should add a Don to an empty array', () => {
        const don: IDon = sampleWithRequiredData;
        expectedResult = service.addDonToCollectionIfMissing([], don);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(don);
      });

      it('should not add a Don to an array that contains it', () => {
        const don: IDon = sampleWithRequiredData;
        const donCollection: IDon[] = [
          {
            ...don,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDonToCollectionIfMissing(donCollection, don);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Don to an array that doesn't contain it", () => {
        const don: IDon = sampleWithRequiredData;
        const donCollection: IDon[] = [sampleWithPartialData];
        expectedResult = service.addDonToCollectionIfMissing(donCollection, don);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(don);
      });

      it('should add only unique Don to an array', () => {
        const donArray: IDon[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const donCollection: IDon[] = [sampleWithRequiredData];
        expectedResult = service.addDonToCollectionIfMissing(donCollection, ...donArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const don: IDon = sampleWithRequiredData;
        const don2: IDon = sampleWithPartialData;
        expectedResult = service.addDonToCollectionIfMissing([], don, don2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(don);
        expect(expectedResult).toContain(don2);
      });

      it('should accept null and undefined values', () => {
        const don: IDon = sampleWithRequiredData;
        expectedResult = service.addDonToCollectionIfMissing([], null, don, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(don);
      });

      it('should return initial array if no Don is added', () => {
        const donCollection: IDon[] = [sampleWithRequiredData];
        expectedResult = service.addDonToCollectionIfMissing(donCollection, undefined, null);
        expect(expectedResult).toEqual(donCollection);
      });
    });

    describe('compareDon', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDon(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDon(entity1, entity2);
        const compareResult2 = service.compareDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDon(entity1, entity2);
        const compareResult2 = service.compareDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDon(entity1, entity2);
        const compareResult2 = service.compareDon(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
