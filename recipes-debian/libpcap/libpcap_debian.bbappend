FILESEXTRAPATHS_prepend := "${THISDIR}/libpcap:"

SRC_URI += " \
    file://libpcap-pkgconfig-support.patch \
    file://0001-Fix-compiler_state_t.ai-usage-when-INET6-is-not-defi.patch \
    file://0002-Add-missing-compiler_state_t-parameter.patch \
"

do_install_prepend () {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    oe_runmake install-shared DESTDIR=${D}
    oe_libinstall -a -so libpcap ${D}${libdir}
    sed "s|@VERSION@|${PV}|" -i ${B}/libpcap.pc
    install -D -m 0644 libpcap.pc ${D}${libdir}/pkgconfig/libpcap.pc
}

