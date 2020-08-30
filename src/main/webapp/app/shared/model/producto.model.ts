export interface IProducto {
  id?: number;
  codigo?: string;
  nombre?: string;
  generoId?: number;
  tallaId?: number;
}

export class Producto implements IProducto {
  constructor(public id?: number, public codigo?: string, public nombre?: string, public generoId?: number, public tallaId?: number) {}
}
