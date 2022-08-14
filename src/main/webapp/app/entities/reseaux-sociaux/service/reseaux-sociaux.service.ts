import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReseauxSociaux, NewReseauxSociaux } from '../reseaux-sociaux.model';

export type PartialUpdateReseauxSociaux = Partial<IReseauxSociaux> & Pick<IReseauxSociaux, 'id'>;

export type EntityResponseType = HttpResponse<IReseauxSociaux>;
export type EntityArrayResponseType = HttpResponse<IReseauxSociaux[]>;

@Injectable({ providedIn: 'root' })
export class ReseauxSociauxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reseaux-sociauxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reseauxSociaux: NewReseauxSociaux): Observable<EntityResponseType> {
    return this.http.post<IReseauxSociaux>(this.resourceUrl, reseauxSociaux, { observe: 'response' });
  }

  update(reseauxSociaux: IReseauxSociaux): Observable<EntityResponseType> {
    return this.http.put<IReseauxSociaux>(`${this.resourceUrl}/${this.getReseauxSociauxIdentifier(reseauxSociaux)}`, reseauxSociaux, {
      observe: 'response',
    });
  }

  partialUpdate(reseauxSociaux: PartialUpdateReseauxSociaux): Observable<EntityResponseType> {
    return this.http.patch<IReseauxSociaux>(`${this.resourceUrl}/${this.getReseauxSociauxIdentifier(reseauxSociaux)}`, reseauxSociaux, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReseauxSociaux>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReseauxSociaux[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReseauxSociauxIdentifier(reseauxSociaux: Pick<IReseauxSociaux, 'id'>): number {
    return reseauxSociaux.id;
  }

  compareReseauxSociaux(o1: Pick<IReseauxSociaux, 'id'> | null, o2: Pick<IReseauxSociaux, 'id'> | null): boolean {
    return o1 && o2 ? this.getReseauxSociauxIdentifier(o1) === this.getReseauxSociauxIdentifier(o2) : o1 === o2;
  }

  addReseauxSociauxToCollectionIfMissing<Type extends Pick<IReseauxSociaux, 'id'>>(
    reseauxSociauxCollection: Type[],
    ...reseauxSociauxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reseauxSociauxes: Type[] = reseauxSociauxesToCheck.filter(isPresent);
    if (reseauxSociauxes.length > 0) {
      const reseauxSociauxCollectionIdentifiers = reseauxSociauxCollection.map(
        reseauxSociauxItem => this.getReseauxSociauxIdentifier(reseauxSociauxItem)!
      );
      const reseauxSociauxesToAdd = reseauxSociauxes.filter(reseauxSociauxItem => {
        const reseauxSociauxIdentifier = this.getReseauxSociauxIdentifier(reseauxSociauxItem);
        if (reseauxSociauxCollectionIdentifiers.includes(reseauxSociauxIdentifier)) {
          return false;
        }
        reseauxSociauxCollectionIdentifiers.push(reseauxSociauxIdentifier);
        return true;
      });
      return [...reseauxSociauxesToAdd, ...reseauxSociauxCollection];
    }
    return reseauxSociauxCollection;
  }
}
