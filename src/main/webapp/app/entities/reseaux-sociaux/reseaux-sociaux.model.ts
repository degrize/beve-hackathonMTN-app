export interface IReseauxSociaux {
  id: number;
  nom?: string | null;
  nomChaine?: string | null;
  lienChaine?: string | null;
}

export type NewReseauxSociaux = Omit<IReseauxSociaux, 'id'> & { id: null };
