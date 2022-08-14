import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonnateur, NewDonnateur } from '../donnateur.model';

export type PartialUpdateDonnateur = Partial<IDonnateur> & Pick<IDonnateur, 'id'>;

type RestOf<T extends IDonnateur | NewDonnateur> = Omit<T, 'dateDebut'> & {
  dateDebut?: string | null;
};

export type RestDonnateur = RestOf<IDonnateur>;

export type NewRestDonnateur = RestOf<NewDonnateur>;

export type PartialUpdateRestDonnateur = RestOf<PartialUpdateDonnateur>;

export type EntityResponseType = HttpResponse<IDonnateur>;
export type EntityArrayResponseType = HttpResponse<IDonnateur[]>;

@Injectable({ providedIn: 'root' })
export class DonnateurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donnateurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donnateur: NewDonnateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donnateur);
    return this.http
      .post<RestDonnateur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(donnateur: IDonnateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donnateur);
    return this.http
      .put<RestDonnateur>(`${this.resourceUrl}/${this.getDonnateurIdentifier(donnateur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(donnateur: PartialUpdateDonnateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donnateur);
    return this.http
      .patch<RestDonnateur>(`${this.resourceUrl}/${this.getDonnateurIdentifier(donnateur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDonnateur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDonnateur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDonnateurIdentifier(donnateur: Pick<IDonnateur, 'id'>): number {
    return donnateur.id;
  }

  compareDonnateur(o1: Pick<IDonnateur, 'id'> | null, o2: Pick<IDonnateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getDonnateurIdentifier(o1) === this.getDonnateurIdentifier(o2) : o1 === o2;
  }

  addDonnateurToCollectionIfMissing<Type extends Pick<IDonnateur, 'id'>>(
    donnateurCollection: Type[],
    ...donnateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const donnateurs: Type[] = donnateursToCheck.filter(isPresent);
    if (donnateurs.length > 0) {
      const donnateurCollectionIdentifiers = donnateurCollection.map(donnateurItem => this.getDonnateurIdentifier(donnateurItem)!);
      const donnateursToAdd = donnateurs.filter(donnateurItem => {
        const donnateurIdentifier = this.getDonnateurIdentifier(donnateurItem);
        if (donnateurCollectionIdentifiers.includes(donnateurIdentifier)) {
          return false;
        }
        donnateurCollectionIdentifiers.push(donnateurIdentifier);
        return true;
      });
      return [...donnateursToAdd, ...donnateurCollection];
    }
    return donnateurCollection;
  }

  protected convertDateFromClient<T extends IDonnateur | NewDonnateur | PartialUpdateDonnateur>(donnateur: T): RestOf<T> {
    return {
      ...donnateur,
      dateDebut: donnateur.dateDebut?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDonnateur: RestDonnateur): IDonnateur {
    return {
      ...restDonnateur,
      dateDebut: restDonnateur.dateDebut ? dayjs(restDonnateur.dateDebut) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDonnateur>): HttpResponse<IDonnateur> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDonnateur[]>): HttpResponse<IDonnateur[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
