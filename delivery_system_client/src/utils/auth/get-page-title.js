const title = 'LWX'
export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return title + pageTitle;
  }
  return title;
}
