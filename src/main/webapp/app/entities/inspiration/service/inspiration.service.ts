import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInspiration, NewInspiration } from '../inspiration.model';

export type PartialUpdateInspiration = Partial<IInspiration> & Pick<IInspiration, 'id'>;

export type EntityResponseType = HttpResponse<IInspiration>;
export type EntityArrayResponseType = HttpResponse<IInspiration[]>;

@Injectable({ providedIn: 'root' })
export class InspirationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inspirations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inspiration: NewInspiration): Observable<EntityResponseType> {
    return this.http.post<IInspiration>(this.resourceUrl, inspiration, { observe: 'response' });
  }

  update(inspiration: IInspiration): Observable<EntityResponseType> {
    return this.http.put<IInspiration>(`${this.resourceUrl}/${this.getInspirationIdentifier(inspiration)}`, inspiration, {
      observe: 'response',
    });
  }

  partialUpdate(inspiration: PartialUpdateInspiration): Observable<EntityResponseType> {
    return this.http.patch<IInspiration>(`${this.resourceUrl}/${this.getInspirationIdentifier(inspiration)}`, inspiration, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInspiration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInspiration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInspirationIdentifier(inspiration: Pick<IInspiration, 'id'>): number {
    return inspiration.id;
  }

  compareInspiration(o1: Pick<IInspiration, 'id'> | null, o2: Pick<IInspiration, 'id'> | null): boolean {
    return o1 && o2 ? this.getInspirationIdentifier(o1) === this.getInspirationIdentifier(o2) : o1 === o2;
  }

  addInspirationToCollectionIfMissing<Type extends Pick<IInspiration, 'id'>>(
    inspirationCollection: Type[],
    ...inspirationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const inspirations: Type[] = inspirationsToCheck.filter(isPresent);
    if (inspirations.length > 0) {
      const inspirationCollectionIdentifiers = inspirationCollection.map(
        inspirationItem => this.getInspirationIdentifier(inspirationItem)!
      );
      const inspirationsToAdd = inspirations.filter(inspirationItem => {
        const inspirationIdentifier = this.getInspirationIdentifier(inspirationItem);
        if (inspirationCollectionIdentifiers.includes(inspirationIdentifier)) {
          return false;
        }
        inspirationCollectionIdentifiers.push(inspirationIdentifier);
        return true;
      });
      return [...inspirationsToAdd, ...inspirationCollection];
    }
    return inspirationCollection;
  }
}
