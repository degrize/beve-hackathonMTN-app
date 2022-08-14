export interface INatureCreateur {
  id: number;
  type?: string | null;
  description?: string | null;
}

export type NewNatureCreateur = Omit<INatureCreateur, 'id'> & { id: null };
