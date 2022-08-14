export interface IInspiration {
  id: number;
  nom?: string | null;
  articleInspiration?: string | null;
}

export type NewInspiration = Omit<IInspiration, 'id'> & { id: null };
