#
# poky rev: 753e2a0ede4449917c75353b57f13bbafe70fac8
# file: https://git.yoctoproject.org/cgit/cgit.cgi/poky/tree/meta/recipes-devtools/apt/apt_1.2.24.bb?id=753e2a0ede4449917c75353b57f13bbafe70fac8
#
DEPENDS = "virtual/libiconv curl db zlib lz4 perl dpkg"
RDEPENDS_${PN} = "dpkg bash debianutils lz4 perl"
require apt.inc

require apt-package.inc

apt-manpages += "doc/apt-key.8"

do_install_append() {
    #Write the correct apt-architecture to apt.conf
    APT_CONF=${D}/etc/apt/apt.conf
    echo 'APT::Architecture "${DPKG_ARCH}";' > ${APT_CONF}
}

