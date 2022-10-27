SUMMARY = "This client is intelligent and can automatically select the appropriate whois server for most queries."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=971fdba71f4a262ae727031e9d631fa8"

inherit debian-package update-alternatives
require recipes-debian/sources/whois.inc

DEPENDS += "gettext-native libidn"

# give higher priority to whois package than inetutils one.
ALTERNATIVE_PRIORITY = "80"
ALTERNATIVE_${PN} = "whois"

do_install() {
    oe_runmake "BASEDIR=${D}" install

    install -d ${D}${docdir}/whois
    install -m 644 README ${D}${docdir}/whois
    install -m 644 debian/copyright ${D}${docdir}/whois
    install -m 644 debian/changelog ${D}${docdir}/whois
}
