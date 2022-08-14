import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieCreateur, NewCategorieCreateur } from '../categorie-createur.model';

export type PartialUpdateCategorieCreateur = Partial<ICategorieCreateur> & Pick<ICategorieCreateur, 'id'>;

export type EntityResponseType = HttpResponse<ICategorieCreateur>;
export type EntityArrayResponseType = HttpResponse<ICategorieCreateur[]>;

@Injectable({ providedIn: 'root' })
export class CategorieCreateurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-createurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categorieCreateur: NewCategorieCreateur): Observable<EntityResponseType> {
    return this.http.post<ICategorieCreateur>(this.resourceUrl, categorieCreateur, { observe: 'response' });
  }

  update(categorieCreateur: ICategorieCreateur): Observable<EntityResponseType> {
    return this.http.put<ICategorieCreateur>(
      `${this.resourceUrl}/${this.getCategorieCreateurIdentifier(categorieCreateur)}`,
      categorieCreateur,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieCreateur: PartialUpdateCategorieCreateur): Observable<EntityResponseType> {
    return this.http.patch<ICategorieCreateur>(
      `${this.resourceUrl}/${this.getCategorieCreateurIdentifier(categorieCreateur)}`,
      categorieCreateur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieCreateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieCreateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorieCreateurIdentifier(categorieCreateur: Pick<ICategorieCreateur, 'id'>): number {
    return categorieCreateur.id;
  }

  compareCategorieCreateur(o1: Pick<ICategorieCreateur, 'id'> | null, o2: Pick<ICategorieCreateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorieCreateurIdentifier(o1) === this.getCategorieCreateurIdentifier(o2) : o1 === o2;
  }

  addCategorieCreateurToCollectionIfMissing<Type extends Pick<ICategorieCreateur, 'id'>>(
    categorieCreateurCollection: Type[],
    ...categorieCreateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorieCreateurs: Type[] = categorieCreateursToCheck.filter(isPresent);
    if (categorieCreateurs.length > 0) {
      const categorieCreateurCollectionIdentifiers = categorieCreateurCollection.map(
        categorieCreateurItem => this.getCategorieCreateurIdentifier(categorieCreateurItem)!
      );
      const categorieCreateursToAdd = categorieCreateurs.filter(categorieCreateurItem => {
        const categorieCreateurIdentifier = this.getCategorieCreateurIdentifier(categorieCreateurItem);
        if (categorieCreateurCollectionIdentifiers.includes(categorieCreateurIdentifier)) {
          return false;
        }
        categorieCreateurCollectionIdentifiers.push(categorieCreateurIdentifier);
        return true;
      });
      return [...categorieCreateursToAdd, ...categorieCreateurCollection];
    }
    return categorieCreateurCollection;
  }
}
