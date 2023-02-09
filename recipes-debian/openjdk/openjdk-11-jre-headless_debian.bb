LICENSE  = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"

inherit debian-package
require recipes-debian/sources/openjdk-11.inc
inherit autotools-brokensep pkgconfig

DEBIAN_UNPACK_DIR = "${WORKDIR}/jdk11u-jdk-${@d.getVar('PV').replace('+', '-')}"

SRC_URI_append = " \
    file://0001-make-autoconf-toolchain-remove-invalid-compiler-chec.patch \
    file://0002-make-lib-Lib-java.smartcardio.gmk-Prevent-host-conta.patch \
"

DEPENDS += " \
    zip-native unzip-native libxslt attr \
    fontconfig freetype libffi \
    giflib libpng zlib jpeg lcms pcsc-lite harfbuzz \
"

PACKAGECONFIG = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11 cups', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa', '', d)} \
"

PACKAGECONFIG[x11] = "--with-x,,libx11 xorgproto libxt libxext libxrender libxtst"
PACKAGECONFIG[cups] = "--with-cups=${STAGING_DIR_HOST}/usr,,cups"
PACKAGECONFIG[alsa] = "--with-alsa,,alsa-lib"

EXTRA_CFLAGS = " \
    -Wdate-time \
    -D_FORTIFY_SOURCE=2 \
    -Wformat \
    -fno-stack-protector \
    -Wno-deprecated-declarations \
    -I${STAGING_INCDIR}/PCSC \
"

EXTRA_LDFLAGS = "-Xlinker -z -Xlinker relro -Xlinker -Bsymbolic-functions"

EXTRA_OECONF_append = " \
    --with-jvm-variants=server \
    --with-boot-jdk=/usr/lib/jvm/java-11-openjdk-amd64 \
    --disable-precompiled-headers \
    --with-jvm-features=zgc,shenandoahgc \
    --disable-ccache \
    --with-debug-level=release \
    --with-native-debug-symbols=zipped \
    --disable-warnings-as-errors \
    --disable-javac-server \
    --with-stdc++lib=dynamic \
    --with-giflib=system \
    --with-libpng=system \
    --with-zlib=system \
    --with-libjpeg=system \
    --with-lcms=system \
    --with-pcsclite=system \
    --with-harfbuzz=system \
    --with-extra-cflags='${CFLAGS} ${EXTRA_CFLAGS}' \
    --with-extra-cxxflags='${CXXFLAGS} ${EXTRA_CFLAGS}' \
    --with-extra-ldflags='${LDFLAGS} ${EXTRA_LDFLAGS}' \
    --with-sysroot=${STAGING_DIR_HOST} \
    --enable-option-checking=yes \
    --with-abi-profile=aarch64 \
    --with-cpu-port=aarch64 \
    --enable-headless-only=yes \
    --enable-nss \
"

EXTRA_BUILD_ENV = "MAKE_VERBOSE=y QUIETLY= LOG=debug IGNORE_OLD_CONFIG=true LIBFFI_LIBS=-lffi_pic JOBS=${@oe.utils.cpu_count()}"

do_configure () {
    cd ${S}
    rm -rf ${S}/build
    mkdir ${S}/build
    cd ${S}/build
    oe_runconf
}

do_compile () {
    cd ${B}
    ${EXTRA_BUILD_ENV} oe_runmake images
}

PACKAGES = "${PN} ${PN}-dbg"

do_install () {
    mkdir -p ${D}${DEBIAN_JDK_BASE_DIR}
    mkdir -p ${D}${DEBIAN_JDK_SYSCONF_DIR}
    rm -rf ${D}${DEBIAN_JDK_BASE_DIR}/jmods
    cp -r --no-preserve=ownership ${B}/images/jdk/bin ${D}${DEBIAN_JDK_BASE_DIR}/bin
    find ${D}${DEBIAN_JDK_BASE_DIR}/bin -type f -not -name 'java' -not -name 'jjs' \
    -not -name 'keytool' -not -name 'pack200' -not -name 'rmid' -not -name 'rmiregistry' \
    -not -name 'unpack200' | xargs -r rm -f
    cp -r --no-preserve=ownership ${B}/images/jdk/conf ${D}${DEBIAN_JDK_BASE_DIR}/conf
    cp -r --no-preserve=ownership ${B}/images/jdk/legal ${D}${DEBIAN_JDK_BASE_DIR}/legal
    cp -r --no-preserve=ownership ${B}/images/jdk/release ${D}${DEBIAN_JDK_BASE_DIR}
    cp -r --no-preserve=ownership ${B}/images/jdk/lib ${D}${DEBIAN_JDK_BASE_DIR}/lib
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/lib/libjawt.so
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/lib/src.zip
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/jmxremote.password.template
    find ${D}${DEBIAN_JDK_BASE_DIR} -name '*.debuginfo' -o -name '*.diz' \
     -o -name 'ADDITIONAL_LICENSE_INFO' -o -name 'LICENSE' | xargs -r rm -f
    chown -R root:root ${D}${DEBIAN_JDK_BASE_DIR}
}

do_install_append () {
    #Based on the debian/rules file, move the conf file to /etc/openjdk-11 and create a symbolic link.
    cp --no-preserve=ownership ${S}/debian/accessibility.properties \
    ${S}/debian/swing.properties \
    ${D}${DEBIAN_JDK_SYSCONF_DIR}/
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/accessibility.properties ${D}${DEBIAN_JDK_BASE_DIR}/conf/accessibility.properties
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/swing.properties ${D}${DEBIAN_JDK_BASE_DIR}/conf/swing.properties
    cp --no-preserve=ownership ${S}/debian/jvm.cfg-default ${D}${DEBIAN_JDK_SYSCONF_DIR}/jvm-arm64.cfg
    cp --no-preserve=ownership ${D}${DEBIAN_JDK_BASE_DIR}/lib/jvm.cfg ${D}${DEBIAN_JDK_BASE_DIR}/lib/jvm.cfg-default
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/lib/jvm.cfg
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/jvm-arm64.cfg ${D}${DEBIAN_JDK_BASE_DIR}/lib/jvm.cfg
    mkdir -p ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/policy
    cp --no-preserve=ownership ${S}/debian/nss.cfg ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/nss.cfg
    cp -r --no-preserve=ownership ${D}${DEBIAN_JDK_BASE_DIR}/conf/security ${D}${DEBIAN_JDK_SYSCONF_DIR}/
    rm -rf ${D}${DEBIAN_JDK_BASE_DIR}/conf/security/*
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/* ${D}${DEBIAN_JDK_BASE_DIR}/conf/security/
    cp -r --no-preserve=ownership ${D}${DEBIAN_JDK_BASE_DIR}/lib/security ${D}${DEBIAN_JDK_SYSCONF_DIR}/
    rm -rf ${D}${DEBIAN_JDK_BASE_DIR}/lib/security/*
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/blocked.certs ${D}${DEBIAN_JDK_BASE_DIR}/lib/security/blocked.certs
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/cacerts ${D}${DEBIAN_JDK_BASE_DIR}/lib/security/cacerts
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/default.policy ${D}${DEBIAN_JDK_BASE_DIR}/lib/security/default.policy
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/security/public_suffix_list.dat ${D}${DEBIAN_JDK_BASE_DIR}/lib/security/public_suffix_list.dat
    cp -r --no-preserve=ownership ${D}${DEBIAN_JDK_BASE_DIR}/lib/jfr ${D}${DEBIAN_JDK_SYSCONF_DIR}/jfr
    rm -rf ${D}${DEBIAN_JDK_BASE_DIR}/lib/jfr
    mkdir -p ${D}${DEBIAN_JDK_BASE_DIR}/lib/jfr
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/jfr/default.jfc ${D}${DEBIAN_JDK_BASE_DIR}/lib/jfr/default.jfc
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/jfr/profile.jfc ${D}${DEBIAN_JDK_BASE_DIR}/lib/jfr/profile.jfc
    mkdir ${D}${DEBIAN_JDK_SYSCONF_DIR}/management
    cp --no-preserve=ownership ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/jmxremote.access \
    ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/management.properties \
    ${D}${DEBIAN_JDK_SYSCONF_DIR}/management/
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/jmxremote.access
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/management.properties
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/management/jmxremote.access ${D}${DEBIAN_JDK_BASE_DIR}//conf/management/jmxremote.access
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/management/management.properties ${D}${DEBIAN_JDK_BASE_DIR}/conf/management/management.properties
    mkdir -p ${D}/usr/share/doc/openjdk-11-jre-headless
    cp --no-preserve=ownership ${S}/debian/JAVA_HOME \
    ${S}/debian/README.Debian \
    ${S}/debian/copyright \
    ${D}${datadir}/doc/${PN}/
    mkdir -p ${D}${DEBIAN_JDK_BASE_DIR}/docs
    ln -rs ${D}${datadir}/doc/${PN} ${D}${DEBIAN_JDK_BASE_DIR}/docs
    cp --no-preserve=ownership ${B}/images/jdk/conf/logging.properties \
    ${D}${DEBIAN_JDK_BASE_DIR}/conf/sound.properties \
    ${D}${DEBIAN_JDK_BASE_DIR}/conf/net.properties \
    ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfontj2d.properties \
    ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfont.properties.ja \
    ${D}${DEBIAN_JDK_SYSCONF_DIR}/
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/conf/sound.properties
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/conf/net.properties
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfontj2d.properties
    rm -f ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfont.properties.ja
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/sound.properties ${D}${DEBIAN_JDK_BASE_DIR}/conf/sound.properties
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/net.properties ${D}${DEBIAN_JDK_BASE_DIR}/conf/net.properties
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/psfontj2d.properties ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfontj2d.properties
    ln -rs ${D}${DEBIAN_JDK_SYSCONF_DIR}/psfont.properties.ja ${D}${DEBIAN_JDK_BASE_DIR}/lib/psfont.properties.ja
    chown -R root:root ${D}
}

FILES_${PN} = " \
    ${DEBIAN_JDK_BASE_DIR}/bin/* \
    ${DEBIAN_JDK_BASE_DIR}/conf/* \
    ${DEBIAN_JDK_BASE_DIR}/lib/* \
    ${DEBIAN_JDK_BASE_DIR}/release \
    ${DEBIAN_JDK_BASE_DIR}/docs/* \
    ${DEBIAN_JDK_BASE_DIR}/legal/* \
    ${datadir}/* \
    ${sysconfdir}/* \
"

B = "${S}/build"

PARALLEL_MAKE=""
PARALLEL_MAKEINST=""

export DEBIAN_JDK_BASE_DIR="${libdir}/jvm/openjdk-11"
export DEBIAN_JDK_SYSCONF_DIR="${sysconfdir}/openjdk-11"

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"

ALTERNATIVE_${PN} = "java"
ALTERNATIVE_LINK_NAME[java] = "${bindir}/java"
ALTERNATIVE_TARGET[java] = "${DEBIAN_JDK_BASE_DIR}/bin/java"
