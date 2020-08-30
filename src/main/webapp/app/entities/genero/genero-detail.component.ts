import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGenero } from 'app/shared/model/genero.model';

@Component({
  selector: 'jhi-genero-detail',
  templateUrl: './genero-detail.component.html'
})
export class GeneroDetailComponent implements OnInit {
  genero: IGenero;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ genero }) => {
      this.genero = genero;
    });
  }

  previousState() {
    window.history.back();
  }
}
