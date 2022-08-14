import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureCreateur, NewNatureCreateur } from '../nature-createur.model';

export type PartialUpdateNatureCreateur = Partial<INatureCreateur> & Pick<INatureCreateur, 'id'>;

export type EntityResponseType = HttpResponse<INatureCreateur>;
export type EntityArrayResponseType = HttpResponse<INatureCreateur[]>;

@Injectable({ providedIn: 'root' })
export class NatureCreateurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-createurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureCreateur: NewNatureCreateur): Observable<EntityResponseType> {
    return this.http.post<INatureCreateur>(this.resourceUrl, natureCreateur, { observe: 'response' });
  }

  update(natureCreateur: INatureCreateur): Observable<EntityResponseType> {
    return this.http.put<INatureCreateur>(`${this.resourceUrl}/${this.getNatureCreateurIdentifier(natureCreateur)}`, natureCreateur, {
      observe: 'response',
    });
  }

  partialUpdate(natureCreateur: PartialUpdateNatureCreateur): Observable<EntityResponseType> {
    return this.http.patch<INatureCreateur>(`${this.resourceUrl}/${this.getNatureCreateurIdentifier(natureCreateur)}`, natureCreateur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INatureCreateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INatureCreateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNatureCreateurIdentifier(natureCreateur: Pick<INatureCreateur, 'id'>): number {
    return natureCreateur.id;
  }

  compareNatureCreateur(o1: Pick<INatureCreateur, 'id'> | null, o2: Pick<INatureCreateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getNatureCreateurIdentifier(o1) === this.getNatureCreateurIdentifier(o2) : o1 === o2;
  }

  addNatureCreateurToCollectionIfMissing<Type extends Pick<INatureCreateur, 'id'>>(
    natureCreateurCollection: Type[],
    ...natureCreateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const natureCreateurs: Type[] = natureCreateursToCheck.filter(isPresent);
    if (natureCreateurs.length > 0) {
      const natureCreateurCollectionIdentifiers = natureCreateurCollection.map(
        natureCreateurItem => this.getNatureCreateurIdentifier(natureCreateurItem)!
      );
      const natureCreateursToAdd = natureCreateurs.filter(natureCreateurItem => {
        const natureCreateurIdentifier = this.getNatureCreateurIdentifier(natureCreateurItem);
        if (natureCreateurCollectionIdentifiers.includes(natureCreateurIdentifier)) {
          return false;
        }
        natureCreateurCollectionIdentifiers.push(natureCreateurIdentifier);
        return true;
      });
      return [...natureCreateursToAdd, ...natureCreateurCollection];
    }
    return natureCreateurCollection;
  }
}
