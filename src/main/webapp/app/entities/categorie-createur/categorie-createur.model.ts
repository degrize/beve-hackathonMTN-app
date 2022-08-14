import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';

export interface ICategorieCreateur {
  id: number;
  categorie?: string | null;
  description?: string | null;
  createurAfricains?: Pick<ICreateurAfricain, 'id' | 'label'>[] | null;
}

export type NewCategorieCreateur = Omit<ICategorieCreateur, 'id'> & { id: null };
