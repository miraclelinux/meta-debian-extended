PACKAGES += "locales"
PROVIDES += "locales"

FILES_locales = "${sbindir}/locale-gen ${sbindir}/update-locale ${sbindir}/validlocale \
    ${libdir}/locale"

RDEPENDS_locales = "glibc perl \
    perl-module-getopt-long \
    perl-module-posix \
    perl-module-carp \
"
DESCRIPTION_locales = "locales utils -- locale-gen, update-locale, validlocale"

RDEPENDS_${PN}-utils = "perl"

do_install_append () {
    if ${@bb.utils.contains('IMAGE_INSTALL', "locales", 'true', 'false', d)}; then
        install -d ${D}/${sbindir}
        install -m 0755 ${S}/debian/local/usr_sbin/* ${D}/${sbindir}
    fi
}

do_poststash_install_cleanup_append() {
    install -d ${D}/${libdir}/locale
}
