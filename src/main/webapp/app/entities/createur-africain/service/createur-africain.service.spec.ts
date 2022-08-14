import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICreateurAfricain } from '../createur-africain.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../createur-africain.test-samples';

import { CreateurAfricainService, RestCreateurAfricain } from './createur-africain.service';

const requireRestSample: RestCreateurAfricain = {
  ...sampleWithRequiredData,
  dateDebut: sampleWithRequiredData.dateDebut?.format(DATE_FORMAT),
};

describe('CreateurAfricain Service', () => {
  let service: CreateurAfricainService;
  let httpMock: HttpTestingController;
  let expectedResult: ICreateurAfricain | ICreateurAfricain[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CreateurAfricainService);
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

    it('should create a CreateurAfricain', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const createurAfricain = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(createurAfricain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CreateurAfricain', () => {
      const createurAfricain = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(createurAfricain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CreateurAfricain', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CreateurAfricain', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CreateurAfricain', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCreateurAfricainToCollectionIfMissing', () => {
      it('should add a CreateurAfricain to an empty array', () => {
        const createurAfricain: ICreateurAfricain = sampleWithRequiredData;
        expectedResult = service.addCreateurAfricainToCollectionIfMissing([], createurAfricain);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(createurAfricain);
      });

      it('should not add a CreateurAfricain to an array that contains it', () => {
        const createurAfricain: ICreateurAfricain = sampleWithRequiredData;
        const createurAfricainCollection: ICreateurAfricain[] = [
          {
            ...createurAfricain,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCreateurAfricainToCollectionIfMissing(createurAfricainCollection, createurAfricain);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CreateurAfricain to an array that doesn't contain it", () => {
        const createurAfricain: ICreateurAfricain = sampleWithRequiredData;
        const createurAfricainCollection: ICreateurAfricain[] = [sampleWithPartialData];
        expectedResult = service.addCreateurAfricainToCollectionIfMissing(createurAfricainCollection, createurAfricain);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(createurAfricain);
      });

      it('should add only unique CreateurAfricain to an array', () => {
        const createurAfricainArray: ICreateurAfricain[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const createurAfricainCollection: ICreateurAfricain[] = [sampleWithRequiredData];
        expectedResult = service.addCreateurAfricainToCollectionIfMissing(createurAfricainCollection, ...createurAfricainArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const createurAfricain: ICreateurAfricain = sampleWithRequiredData;
        const createurAfricain2: ICreateurAfricain = sampleWithPartialData;
        expectedResult = service.addCreateurAfricainToCollectionIfMissing([], createurAfricain, createurAfricain2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(createurAfricain);
        expect(expectedResult).toContain(createurAfricain2);
      });

      it('should accept null and undefined values', () => {
        const createurAfricain: ICreateurAfricain = sampleWithRequiredData;
        expectedResult = service.addCreateurAfricainToCollectionIfMissing([], null, createurAfricain, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(createurAfricain);
      });

      it('should return initial array if no CreateurAfricain is added', () => {
        const createurAfricainCollection: ICreateurAfricain[] = [sampleWithRequiredData];
        expectedResult = service.addCreateurAfricainToCollectionIfMissing(createurAfricainCollection, undefined, null);
        expect(expectedResult).toEqual(createurAfricainCollection);
      });
    });

    describe('compareCreateurAfricain', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCreateurAfricain(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCreateurAfricain(entity1, entity2);
        const compareResult2 = service.compareCreateurAfricain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCreateurAfricain(entity1, entity2);
        const compareResult2 = service.compareCreateurAfricain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCreateurAfricain(entity1, entity2);
        const compareResult2 = service.compareCreateurAfricain(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
