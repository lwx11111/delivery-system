// import defaultSettings from '@/settings.js'

// const title = defaultSettings.title || 'Vue Element Admin'
const title = 'LWX'
export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return title + pageTitle;
  }
  return title;
}
