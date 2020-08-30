export interface IInventario {
  id?: number;
  stock?: number;
  productoId?: number;
}

export class Inventario implements IInventario {
  constructor(public id?: number, public stock?: number, public productoId?: number) {}
}
