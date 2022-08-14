import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDonnateur } from '../donnateur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../donnateur.test-samples';

import { DonnateurService, RestDonnateur } from './donnateur.service';

const requireRestSample: RestDonnateur = {
  ...sampleWithRequiredData,
  dateDebut: sampleWithRequiredData.dateDebut?.format(DATE_FORMAT),
};

describe('Donnateur Service', () => {
  let service: DonnateurService;
  let httpMock: HttpTestingController;
  let expectedResult: IDonnateur | IDonnateur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonnateurService);
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

    it('should create a Donnateur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const donnateur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(donnateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Donnateur', () => {
      const donnateur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(donnateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Donnateur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Donnateur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Donnateur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonnateurToCollectionIfMissing', () => {
      it('should add a Donnateur to an empty array', () => {
        const donnateur: IDonnateur = sampleWithRequiredData;
        expectedResult = service.addDonnateurToCollectionIfMissing([], donnateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donnateur);
      });

      it('should not add a Donnateur to an array that contains it', () => {
        const donnateur: IDonnateur = sampleWithRequiredData;
        const donnateurCollection: IDonnateur[] = [
          {
            ...donnateur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDonnateurToCollectionIfMissing(donnateurCollection, donnateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Donnateur to an array that doesn't contain it", () => {
        const donnateur: IDonnateur = sampleWithRequiredData;
        const donnateurCollection: IDonnateur[] = [sampleWithPartialData];
        expectedResult = service.addDonnateurToCollectionIfMissing(donnateurCollection, donnateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donnateur);
      });

      it('should add only unique Donnateur to an array', () => {
        const donnateurArray: IDonnateur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const donnateurCollection: IDonnateur[] = [sampleWithRequiredData];
        expectedResult = service.addDonnateurToCollectionIfMissing(donnateurCollection, ...donnateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donnateur: IDonnateur = sampleWithRequiredData;
        const donnateur2: IDonnateur = sampleWithPartialData;
        expectedResult = service.addDonnateurToCollectionIfMissing([], donnateur, donnateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donnateur);
        expect(expectedResult).toContain(donnateur2);
      });

      it('should accept null and undefined values', () => {
        const donnateur: IDonnateur = sampleWithRequiredData;
        expectedResult = service.addDonnateurToCollectionIfMissing([], null, donnateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donnateur);
      });

      it('should return initial array if no Donnateur is added', () => {
        const donnateurCollection: IDonnateur[] = [sampleWithRequiredData];
        expectedResult = service.addDonnateurToCollectionIfMissing(donnateurCollection, undefined, null);
        expect(expectedResult).toEqual(donnateurCollection);
      });
    });

    describe('compareDonnateur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDonnateur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDonnateur(entity1, entity2);
        const compareResult2 = service.compareDonnateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDonnateur(entity1, entity2);
        const compareResult2 = service.compareDonnateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDonnateur(entity1, entity2);
        const compareResult2 = service.compareDonnateur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
