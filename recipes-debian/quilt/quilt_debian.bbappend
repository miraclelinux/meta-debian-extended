RDEPENDS_${PN}-ptest += "coreutils patch"

do_install_append() {
    # https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=909398
    rm ${S}/test/push_timeskew.test
}
