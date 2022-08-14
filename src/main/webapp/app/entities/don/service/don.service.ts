import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDon, NewDon } from '../don.model';

export type PartialUpdateDon = Partial<IDon> & Pick<IDon, 'id'>;

type RestOf<T extends IDon | NewDon> = Omit<T, 'dateDon'> & {
  dateDon?: string | null;
};

export type RestDon = RestOf<IDon>;

export type NewRestDon = RestOf<NewDon>;

export type PartialUpdateRestDon = RestOf<PartialUpdateDon>;

export type EntityResponseType = HttpResponse<IDon>;
export type EntityArrayResponseType = HttpResponse<IDon[]>;

@Injectable({ providedIn: 'root' })
export class DonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(don: NewDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(don);
    return this.http.post<RestDon>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(don: IDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(don);
    return this.http
      .put<RestDon>(`${this.resourceUrl}/${this.getDonIdentifier(don)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(don: PartialUpdateDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(don);
    return this.http
      .patch<RestDon>(`${this.resourceUrl}/${this.getDonIdentifier(don)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDon[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDonIdentifier(don: Pick<IDon, 'id'>): number {
    return don.id;
  }

  compareDon(o1: Pick<IDon, 'id'> | null, o2: Pick<IDon, 'id'> | null): boolean {
    return o1 && o2 ? this.getDonIdentifier(o1) === this.getDonIdentifier(o2) : o1 === o2;
  }

  addDonToCollectionIfMissing<Type extends Pick<IDon, 'id'>>(donCollection: Type[], ...donsToCheck: (Type | null | undefined)[]): Type[] {
    const dons: Type[] = donsToCheck.filter(isPresent);
    if (dons.length > 0) {
      const donCollectionIdentifiers = donCollection.map(donItem => this.getDonIdentifier(donItem)!);
      const donsToAdd = dons.filter(donItem => {
        const donIdentifier = this.getDonIdentifier(donItem);
        if (donCollectionIdentifiers.includes(donIdentifier)) {
          return false;
        }
        donCollectionIdentifiers.push(donIdentifier);
        return true;
      });
      return [...donsToAdd, ...donCollection];
    }
    return donCollection;
  }

  protected convertDateFromClient<T extends IDon | NewDon | PartialUpdateDon>(don: T): RestOf<T> {
    return {
      ...don,
      dateDon: don.dateDon?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDon: RestDon): IDon {
    return {
      ...restDon,
      dateDon: restDon.dateDon ? dayjs(restDon.dateDon) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDon>): HttpResponse<IDon> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDon[]>): HttpResponse<IDon[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
