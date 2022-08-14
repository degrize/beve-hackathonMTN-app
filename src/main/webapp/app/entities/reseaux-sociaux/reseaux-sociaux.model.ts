import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';

export interface IReseauxSociaux {
  id: number;
  nom?: string | null;
  nomChaine?: string | null;
  lienChaine?: string | null;
  createurAfricains?: Pick<ICreateurAfricain, 'id' | 'label'>[] | null;
}

export type NewReseauxSociaux = Omit<IReseauxSociaux, 'id'> & { id: null };
