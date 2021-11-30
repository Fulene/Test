@Pipe({
  name: 'sortBy',
})
export class SortByPipe implements PipeTransform {
  transform(values: any, field: string): any[] {
    if (!values && !Array.isArray(values)) {
      return [];
    }
    return _.sortBy(values, v => v[field] && isNaN(v[field])
      ? v[field].toString().toLowerCase()
      : parseInt(v[field]));
  }
}